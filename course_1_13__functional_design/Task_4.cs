using System.Collections.Generic;

namespace FunctionalDesign
{
    public static class CascadeProcessor
    {
        public static BoardState ProcessCascade(BoardState currentState)
        {
            List<Match> matches = MatchFinder.FindMatches(currentState.Board);
            if (matches.Count == 0)
            {
                return currentState;
            }

            return currentState
                .Pipe(state => MatchRemover.RemoveMatches(state, matches))
                .Pipe(Game.FillEmptySpaces)
                .Pipe(ProcessCascade);
        }
    }
}