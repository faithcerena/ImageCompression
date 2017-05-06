public class Node {
	private int frequency;
	private int character;
	private String bitString;
	public Node next;
	public Node left = null;
	public Node right = null;

	public Node(){}

	public Node(int frequency, int character){
		this.frequency = frequency;
		this.character = character;
	}

	public void setFrequency(int frequency){
		this.frequency = frequency;
	}

	public void setBitString(String bitString){
		this.bitString = bitString;
	}

	public String getBitString(){
		return bitString;
	}

	public void setChar(int character){
		this.character = character;
	}

	public int getFrequency(){
		return frequency;
	}

	public int getChar(){
		return character;
	}
}
