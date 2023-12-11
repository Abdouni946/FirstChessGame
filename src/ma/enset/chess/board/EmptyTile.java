package ma.enset.chess.board;

import ma.enset.chess.pieces.Piece;

public class EmptyTile extends Tile{
    public EmptyTile(final int coordinate) {
        super(coordinate);
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
