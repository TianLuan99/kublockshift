package eurekatluan.io.blockchain;

import eurekatluan.io.utils.StringUtil;

import java.util.Date;

public class KuBlock {
    public String hash;
    public String prevHash;
    private String data;
    private long timestamp;
    private int nonce;

    /**
     * Initialize the KuBlock instance by the data and previous block's hash.
     *
     * @param data is the data of this block.
     * @param prevHash is the hash of previous block.
     */
    public KuBlock(String data, String prevHash) {
        this.data = data;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.hash = this.calculateHash();
    }

    /**
     * Calculate the hash of this block by previous block's hash, timestamp and data.
     *
     * @return the hash of this block.
     */
    public String calculateHash() {
        String calculatedHash =
                StringUtil.applySha256(prevHash + timestamp + nonce + data);
        return calculatedHash;
    }

    /**
     * Take in an int called difficulty, this is the number of 0’s they must solve for.
     *
     * @param difficulty this is the number of 0’s they must solve for.
     */
    public void mineBlock(int difficulty) {
        // Create a string with difficulty * "0"
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Mined a Block : " + hash);
    }
}
