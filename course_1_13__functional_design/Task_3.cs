using System;

namespace FunctionalDesign
{
    public static class FunctionalExtensions
    {
        public static TResult Pipe<TSource, TResult>(
            this TSource source,
            Func<TSource, TResult> transform)
        {
            return transform(source);
        }
    }

    public static partial class Game
    {
        static Random r = new Random();
        static char[] symbols = { 'A', 'B', 'C', 'D', 'E', 'F' };

        public static void Draw(Board board)
        {
            Console.WriteLine("  0 1 2 3 4 5 6 7");
            for (int i = 0; i < 8; i++)
            {
                Console.Write(i + " ");
                for (int j = 0; j < 8; j++)
                {
                    Console.Write(board.cells[i, j].Symbol + " ");
                }

                Console.WriteLine();
            }

            Console.WriteLine();
        }

        public static Board CloneBoard(Board board)
        {
            Board b = new Board(board.size);
            for (int row = 0; row < board.size; row++)
            for (int col = 0; col < board.size; col++)
                b.cells[row, col] = board.cells[row, col];
            return b;
        }

        public static BoardState ReadMove(BoardState bs)
        {
            Console.WriteLine(">");
            string input = Console.ReadLine();
            if (input == "q")
                Environment.Exit(0);

            Board board = CloneBoard(bs.Board);
            string[] coords = input.Split(' ');
            int x = int.Parse(coords[1]);
            int y = int.Parse(coords[0]);
            int x1 = int.Parse(coords[3]);
            int y1 = int.Parse(coords[2]);
            Element e = board.cells[x, y];
            board.cells[x, y] = board.cells[x1, y1];
            board.cells[x1, y1] = e;
            BoardState bb = new BoardState(board, bs.Score);
            return bb;
        }

        public static BoardState FillEmptySpaces(BoardState currentState)
        {
            if (currentState.Board.cells == null)
                return currentState;

            Element[,] newCells = (Element[,])currentState.Board.cells.Clone();

            for (int row = 0; row < currentState.Board.size; row++)
            {
                for (int col = 0; col < currentState.Board.size; col++)
                {
                    if (newCells[row, col].Symbol == Element.EMPTY)
                    {
                        newCells[row, col] = new Element
                        {
                            Symbol = symbols[r.Next(symbols.Length)]
                        };
                    }
                }
            }

            return new BoardState(
                new Board { size = currentState.Board.size, cells = newCells },
                currentState.Score
            );
        }

        public static BoardState InitializeGame(int boardSize = 8)
        {
            return new BoardState(new Board(boardSize), 0)
                .Pipe(FillEmptySpaces)
                .Pipe(CascadeProcessor.ProcessCascade);
        }
    }
}