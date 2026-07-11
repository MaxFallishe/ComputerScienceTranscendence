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

            BoardState bsWithRemovedMatches = MatchRemover.RemoveMatches(currentState, matches);
            BoardState bsWithFilledEmptySpaces = Game.FillEmptySpaces(bsWithRemovedMatches);

            return ProcessCascade(bsWithFilledEmptySpaces);
        }
    }
}