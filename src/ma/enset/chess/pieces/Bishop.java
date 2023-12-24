package ma.enset.chess.pieces;

import com.google.common.collect.ImmutableList;
import ma.enset.chess.Alliance;
import ma.enset.chess.board.Board;
import ma.enset.chess.board.BoardUtils;
import ma.enset.chess.board.Move;
import ma.enset.chess.board.Move.AttackMove;
import ma.enset.chess.board.Move.MajorMove;
import ma.enset.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {
    private final static int[] LEGAL_MOVES_VECTOR_OFFSETS = {-9, -7, +7, +9};

    public Bishop(final int position, final Alliance alliance) {
        super(position, pieceType.Bishop, alliance);
    }

    @Override
    public Collection<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : LEGAL_MOVES_VECTOR_OFFSETS) {
            int destinationCoordinate = this.position;
            while (BoardUtils.isValidTileCoor(destinationCoordinate)) {
                if (isFirstColExclusion(destinationCoordinate, candidateOffset) || isEighthColExclusion(destinationCoordinate, candidateOffset)) {
                    break;
                }
                destinationCoordinate += candidateOffset;
                if (BoardUtils.isValidTileCoor(destinationCoordinate)) {
                    final Tile destinationTile = board.getTile(destinationCoordinate);
                    if (!destinationTile.isOccupied()) {
                        legalMoves.add(new MajorMove(board, this, destinationCoordinate));
                    } else {
                        final Piece occupingPiece = destinationTile.getPiece();
                        final Alliance occupingPieceAlliance = occupingPiece.getAlliance();
                        if (this.alliance != occupingPieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, destinationCoordinate, occupingPiece));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getAlliance());
    }

    @Override
    public String toString() {
        return pieceType.Bishop.toString();
    }

    private static boolean isFirstColExclusion(final int currentPos, final int offset) {
        return BoardUtils.FIRST_COL[currentPos] && ((offset == -9) || (offset == 7));
    }

    private static boolean isEighthColExclusion(final int currentPos, final int offset) {
        return BoardUtils.EIGHTH_COL[currentPos] && ((offset == -7) || (offset == 9));
    }
}
