// Don't place your source in a package
import java.math.BigInteger;
import java.util.*;
import java.lang.*;

// Please name your class Main
class Main {
    static HashMap<String, ArrayList<String>> relations = new HashMap<>();
    static LinkedHashMap<TradeItem, Integer> trades = new LinkedHashMap<>();


    public static void main(String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();

        for (int i = 0; i < a; i++) {
            String item1 = in.next();
            String item2 = in.next();
            TradeItem temp = new TradeItem();
            temp.setFood1(item1);
            temp.setFood2(item2);
            trades.put(temp, in.nextInt());
            if (relations.containsKey(item1)) {
                relations.get(item1).add(item2);
            } else {
                ArrayList<String> tempa = new ArrayList<>();
                tempa.add(item2);
                relations.put(item1, tempa);
            }
        }

        int numTrades = in.nextInt();
        for (int i = 0; i < numTrades; i++) {
            String item1 = in.next();
            String item2 = in.next();
            StringBuilder toPrint = new StringBuilder();
            String trade = findTrade(item1, item1, item2);
            if (item1.equalsIgnoreCase(item2)) {
                toPrint.append(1);
            }
            else if(trade.isEmpty()){
                toPrint.append(-1);
            }
            else{
                trade = item1 + "," + trade;
                BigInteger tradeValue = makeTrade(trade);
                toPrint.append(tradeValue.toString());
            }
            System.out.println(toPrint);
        }


    }

    static BigInteger makeTrade(String tradePath){
        String[] path = tradePath.split(",");
        BigInteger tradeCost = new BigInteger("1");

        Set<TradeItem> keys = trades.keySet();

        for (int i = 0; i < path.length - 1; i++) {
            TradeItem potentialTrade = new TradeItem(path[i], path[i+1]);
            for (TradeItem item :
                    keys) {
                if(item.equals(potentialTrade)){
                    tradeCost = tradeCost.multiply(new BigInteger(trades.get(item).toString())).mod(new BigInteger("998244353"));
                }
            }
        }
        return tradeCost;
    }

    static String findTrade(String origin, String item1, String item2) {
        ArrayList<String> nextItem;
        if(relations.containsKey(item1)) {
            nextItem = relations.get(item1);
        }
        else return "";

        if (nextItem.contains(item2) || item1==null) {
            return item2;
        } else if (item1.equalsIgnoreCase(item2)) {
            return "";
        } else {
            for (String item :
                    nextItem) {
                String ret = findTrade(item1, item, item2);
                if (!ret.equalsIgnoreCase("")) {
                    return item + "," + ret;
                }
                else if(item.equalsIgnoreCase(origin)){
                    return "";
                }
            }
        }
        return "You fucked up";
    }
}

class TradeItem {
    private String food1;
    private String food2;

    public TradeItem() {
    }

    public TradeItem(String food1, String food2) {
        this.food1 = food1;
        this.food2 = food2;
    }

    public String getFood1() {
        return food1;
    }

    public void setFood1(String food1) {
        this.food1 = food1;
    }

    public String getFood2() {
        return food2;
    }

    public void setFood2(String food2) {
        this.food2 = food2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItem tradeItem = (TradeItem) o;

        if (!food1.equals(tradeItem.food1)) return false;
        return food2.equals(tradeItem.food2);
    }
}

