package UNOGame;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> cards;
    private GameInfo info;
    int ID;
    MetaInfo mi = new MetaInfo();

    public void addCard(final Card c) {
        this.cards.add(c);
    }

    public Player(final int iD) {
        ID = iD;
        this.cards = new ArrayList<>();
    }

    public void updateInfo(final GameInfo i) {
        this.info = i;
    }

    public Card handOut() {
        for (final Card c : this.cards) {
            boolean ok = ((this.info.getR().color == null || this.info.getR().color.equals(c.getColor())));
            ok |= (this.info.getR().number == c.getNum());
            if (this.info.getR().wildNum > 0) {
                if (c.getType().equals(mi.wildType[1]) || c.getType().equals(mi.cardType[0])) {
                    ok = true;
                } else {
                    ok = false;
                }
            }
            if (ok)
                return c;
        }
        return null;
    }

    public void discard(final int cardID) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getID() == cardID)
                this.cards.remove(i);
        }
    }

    public int selectColor() {
        return 1;
    }

    public List<Card> getCards() {
        return cards;
    }
}