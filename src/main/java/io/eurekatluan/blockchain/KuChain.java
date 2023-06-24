package io.eurekatluan.blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class KuChain {

    public static ArrayList<KuBlock> kuBlockchain = new ArrayList<>();
    public static int difficulty = 5; // hardcode the difficulty for now

    /**
     * Check if this blockchain is valid.
     *
     * @return true if this blockchain is valid, otherwise false
     */
    public static Boolean isChainValid() {
        KuBlock curBlock;
        KuBlock prevBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // loop through the block to check hashes
        for (int i = 1; i < kuBlockchain.size(); i++) {
            curBlock = kuBlockchain.get(i);
            prevBlock = kuBlockchain.get(i - 1);
            // compare registered hash and calculated hash
            if (!curBlock.hash.equals(curBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            // compare previous hash and registered previous hash
            if (!prevBlock.hash.equals(curBlock.prevHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            // check if hash is solved
            if(!curBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        kuBlockchain.add(new KuBlock("first block", "0"));
        System.out.println("Trying to mine block 1...");
        kuBlockchain.get(0).mineBlock(difficulty);

        kuBlockchain.add(new KuBlock("second block", kuBlockchain.get(kuBlockchain.size() - 1).hash));
        System.out.println("Trying to mine block 2...");
        kuBlockchain.get(1).mineBlock(difficulty);

        kuBlockchain.add(new KuBlock("third block", kuBlockchain.get(kuBlockchain.size() - 1).hash));
        System.out.println("Trying to mine block 3...");
        kuBlockchain.get(2).mineBlock(difficulty);

        System.out.println("Blockchain is Valid: " + isChainValid());

        String kuBlockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(kuBlockchain);
        System.out.println("The block chain: ");
        System.out.println(kuBlockchainJson);
    }
}
