package escape.component;

import escape.gamedef.EscapePiece;
import escape.gamedef.Player;
import escape.util.PieceTypeDescriptor;

public class MyPiece implements EscapePiece {
    private final PieceTypeDescriptor descriptor;
    private final Player player;

    /**
     * The constructor takes a piece descriptor and a player
     * @param descriptor the descriptor
     * @param player the player
     */
    public MyPiece(PieceTypeDescriptor descriptor, Player player) {
        this.descriptor = descriptor;
        this.player = player;
    }

    /**
     * Get the piece descriptor
     * @return the piece descriptor
     */
    public PieceTypeDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PieceName getName() {
        return this.descriptor.getPieceName();
    }
}
