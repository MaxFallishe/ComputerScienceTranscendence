using System.Collections.Generic;

namespace FunctionalDesign
{
    public static class CascadeProcessor
    {
        public static BoardState ProcessCascade(BoardState currentState)
        {
            bool debugMode = true /* Получаем из конфига или аргументов */;
            List<Match> matches = MatchFinder.FindMatches(currentState.Board);
            return matches.Count == 0
                ? currentState
                : ProcessCascadeLoop(currentState, matches, debugMode)
                    .Pipe(ProcessCascade);
        }

        private static BoardState ProcessCascadeLoop(
            BoardState currentState, List<Match> matches, bool debugMode)
        {
            return currentState
                .Pipe(bs => MatchRemover.RemoveMatches(bs, matches))
                .Draw(debugMode)
                .Pipe(Game.FillEmptySpaces)
                .Draw(debugMode);
        }
    }
}