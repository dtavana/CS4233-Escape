package escape.component;

import escape.gamedef.EscapePiece;
import escape.gamedef.Player;
import escape.util.PieceTypeDescriptor;

public class MyPiece implements EscapePiece {
    private PieceTypeDescriptor descriptor;
    private Player player;

    public MyPiece(PieceTypeDescriptor descriptor, Player player) {
        this.descriptor = descriptor;
        this.player = player;
    }

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

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "MyPiece [descriptor=" + descriptor + ", player=" + player + "]";
    }
}
