// Don't place your source in a package
import java.math.BigInteger;
import java.util.*;
import java.lang.*;

// Please name your class Main
class Main {
//    static HashMap<String, ArrayList<String>> relations = new HashMap<>();
//    static LinkedHashMap<String, Integer> trades = new LinkedHashMap<>();
	static HashMap<String, ItemProperties> items = new HashMap<String, ItemProperties>();

	
    public static void main(String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();

        for (int i = 0; i < a; i++) {
            String item1 = in.next();
            String item2 = in.next();
            
            items.put(item1 + " " + item2, new ItemProperties(in.nextInt()));
            if (items.containsKey(item1)) {
                items.get(item1).add(item2);
            } else {
                items.put(item1, new ItemProperties(item2));
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

        Set<String> keys = items.keySet();

        for (int i = 0; i < path.length - 1; i++) {
            String potentialTrade = path[i] + " " + path[i+1];
            for (String item : keys) {
                if(item.equals(potentialTrade)) {
                    tradeCost = tradeCost.multiply(new BigInteger(items.get(item).toString())).mod(new BigInteger("998244353"));
                }
            }
        }
        return tradeCost;
    }

    static String findTrade(String origin, String item1, String item2) {
        ArrayList<String> nextItem;
        if(items.containsKey(item1)) {
            nextItem = items.get(item1);
        }
        else return "";

        if (nextItem.contains(item2) || item1==null) {
            return item2;
        } else if (item1.equalsIgnoreCase(item2)) {
            return "";
        } else {
            for (String item : nextItem) {
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

class ItemProperties extends ArrayList<String> {
	int tradeValue;
	
	
	public ItemProperties(int tradeValue) {
		this.tradeValue = tradeValue;
	}
	
	public ItemProperties(String others) {
		add(others);
	}
}