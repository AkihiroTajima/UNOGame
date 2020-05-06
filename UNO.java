package UNOGame;

public class UNO {
    Player[] playerList;
    int numPlayer;
    GameInfo info;
    MetaInfo mi = new MetaInfo();

    UNO(int numPlayer) {

        this.numPlayer = numPlayer;
        playerList = new Player[this.numPlayer];
        // プレイヤーの用意
        for (int i = 0; i < this.numPlayer; i++) {
            playerList[i] = new Player(i);
        }
        info = new GameInfo();

        // カードの用意
        int id = 0;
        for (int i = 0; i < 2; i++) {
            for (final String col : mi.colors) {
                for (final String type : mi.cardType) {
                    info.addCard(new Card(type, -1, col, mi.stack, id));
                    id++;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (final String type : mi.wildType) {
                info.addCard(new Card(type, -1, null, mi.stack, id));
                id++;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int num = 1; num <= 9; num++) {
                for (final String col : mi.colors) {
                    info.addCard(new Card(mi.num, num, col, mi.stack, id));
                    id++;
                }
            }
        }

        // 山札をシャッフル
        info.shuffleCard();
        // 手札を配る
        int cnt = 0;
        for (int i = 0; i < this.numPlayer; i++) {
            for (int c = 0; c < 7; c++) {
                distribute(i, info.getCards().get(cnt));
                cnt++;
            }
        }
        // 場のカードを一枚めくる
        final Card dc = this.info.getCards().get(cnt);
        this.info.deck.add(dc);
        this.info.setOwner(dc.getID(), mi.deck);
        this.info.setR(dc.getColor(), dc.getNum());
    }

    void distribute(final int playerID, final Card dc) {
        playerList[playerID].addCard(dc);
        this.info.setOwner(dc.getID(), Integer.toString(playerList[playerID].ID));
    }

    void drawCard(final int idx) {
        this.playerList[idx].updateInfo(this.info);
        final Card c = this.playerList[idx].handOut();
        System.out.println("\tStrict: color = " + this.info.getR().color + " number = " + this.info.getR().number + " wildNum = " + this.info.getR().wildNum);
        if (c == null) {
            int cnt = 0;
                int numDraw;
                if (this.info.getR().wildNum > 0) numDraw = this.info.getR().wildNum;
                else numDraw = 2;
                for (Card card : this.info.getCards()) {
                    if ( card.getOwner().equals(this.mi.stack)) {
                        distribute(idx, card);
                        cnt++;
                    }
                    if (cnt >= numDraw) break;
                }
                this.info.setRWildnum(0);
            System.out.println("\tNot Submitted Card");  
        } else {
            boolean ok = ((this.info.getR().color == null || this.info.getR().color.equals(c.getColor())));
            ok |= (this.info.getR().number == c.getNum());
            if (this.info.getR().wildNum > 0) {
                if (c.getType().equals(mi.wildType[1]) || c.getType().equals(mi.cardType[0])) {
                    ok = true;
                } else {
                    ok = false;
                }
            }

            if (ok) {
                this.info.deck.add(c);
                this.playerList[idx].discard(c.getID());
                if (c.getType().equals(mi.wildType[0])) { // wild
                    int color = this.playerList[idx].selectColor();
                    System.out.println("\t"+c);
                    System.out.println("\tNext Color = " + mi.colors[color]);
                    this.info.setR(mi.colors[color], c.getNum());
                } else if (c.getType().equals(mi.wildType[1])) { // wildDraw2
                    int color = this.playerList[idx].selectColor();
                    System.out.println("\t"+c);
                    System.out.println("\tNext Color = " + mi.colors[color]);
                    this.info.setR(mi.colors[color], c.getNum());
                    this.info.setRWildnum(2);
                } else if (c.getType().equals(mi.cardType[0])) { // draw1
                    this.info.setRWildnum(1);
                    System.out.println("\t"+c);
                    this.info.setR(c.getColor(), c.getNum());
                } else if (c.getType().equals(mi.cardType[1])) { // reverse
                    this.info.reverse();
                    System.out.println("\t"+c);
                    this.info.setR(c.getColor(), c.getNum());
                } else if (c.getType().equals(mi.cardType[2])) { // skip
                    this.info.requestSkip();
                    System.out.println("\t"+c);
                    this.info.setR(c.getColor(), c.getNum());
                } else {
                    System.out.println("\t"+c);
                    this.info.setR(c.getColor(), c.getNum());
                }
                System.out.println("\tCard Accepted");  
            } else {
                int cnt = 0;
                int numDraw;
                if (this.info.getR().wildNum > 0) numDraw = this.info.getR().wildNum;
                else numDraw = 2;
                for (Card card : this.info.getCards()) {
                    if ( card.getOwner().equals(this.mi.stack)) {
                        distribute(idx, card);
                        cnt++;
                    }
                    if (cnt >= numDraw) break;
                }
                this.info.setRWildnum(0);
                System.out.println("\tWrong Card");
            }
        }
    }

    boolean gameEndCheck() {
        int numStack = 0;
        for (final Card c : this.info.getCards()) {
            if (c.getOwner().equals(mi.stack))
                numStack++;
        }
        if (numStack < Math.max(this.info.getR().wildNum, 2))
            return true;

        for (final Player p : this.playerList) {
            if (p.getCards().size() == 0)
                return true;
        }
        return false;
    }

    void game() {
        int turn = 0;
        int banpei = 0;
        boolean isGameEnd = false;
        while (!isGameEnd && banpei < 100) {
            // カードを出す
            System.out.println("Player: " + turn);
            drawCard(turn);
            // 修了条件チェック
            isGameEnd = gameEndCheck();

            int next = 1 + this.info.skip();
            if (!this.info.getReverse()) {
                turn = (turn + next) % this.numPlayer;
            } else { 
                if (turn-next < 0) {
                    turn = this.numPlayer - (1+next-turn);
                } else {
                    turn -= next;
                }
                    
            }

            banpei++;
            System.out.println(banpei);
        }

        System.out.println("\nRESULT\n------------------------------------\n");
        for (Player p : this.playerList) {
            System.out.println("Player: " + p.ID);
            System.out.println("Number of cards = " + p.getCards().size());
            for (Card c : p.getCards()) {
                System.out.println(c);
            }
            System.out.println("--- \n");

        }
    }

    public static void main(final String[] args) {
        final UNO u = new UNO(3);
        // u.info.getCards().stream().forEach(System.out::println);
        // System.out.println(u.info.getCards().size());

        u.game();
    }
}