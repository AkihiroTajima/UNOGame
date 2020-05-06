package UNOGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInfo {
    List<Card> deck;
    private final List<Card> cards;
    private final Ristrict r;
    boolean reverse = false;
    boolean isSkip = false;


    GameInfo() {
        this.deck = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.r = new Ristrict();
    }

    public void shuffleCard() {
        Collections.shuffle(this.cards);
    }

    public void addCard(final Card c) {
        this.cards.add(c);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setOwner(final int id, final String owner) {
        for (final Card c : this.cards) {
            if (c.getID() == id) {
                c.setOwner(owner);
                return;
            }
        }
        System.err.println("Not FOUND Card: " + id);
        System.exit(1);
    }

    class Ristrict {
        String color = null;
        int number = -1;
        int wildNum = 0;
    
        public void set(final String c, final int n) {
            this.color = c;
            this.number = n;
        }

        public int getWildNum() {
            return wildNum;
        }

        public void addWildNum1() {
            this.wildNum += 1;
        }

        public void addWildNum2() {
            this.wildNum += 2;
        }
        public void resetWildNum() {
            this.wildNum = 0;
        }
    }

    public void setRWildnum(int num) {
        if (num == 1) this.r.addWildNum1();
        else if (num == 2) this.r.addWildNum2();
        else if (num == 0) this.r.resetWildNum();
        else {
            System.err.println("Invalid Input Wild Num");
        }
    }

    public Ristrict getR() {
        return r;
    } 

    public void setR(final String c, final int n) {
        this.r.set(c, n);
    }

    public void reverse() {
        this.reverse = !this.reverse;
    }

    public boolean getReverse() {
        return this.reverse;
    }

    public void requestSkip() {
        this.isSkip = true;
    }

    public int skip() {
        if (this.isSkip) {
            this.isSkip = false;
            return 1;
        } else {
            return 0;
        }
    }
}