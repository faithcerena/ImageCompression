import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class HuffmanTree{
  int size, ctr = 0, arrCount = 0;
  int[] intArray;
  static int[] rgbCharacters;
  int width, height;

  Node first = null;

  String code = "", binaryCode = "", imageCode = "";

  public HuffmanTree(int size, int[] intArray, int width, int height){
    this.size = size;
    this.intArray = intArray;
    this.width = width;
    this.height = height;
    rgbCharacters = new int[width*height];
    System.out.println(rgbCharacters.length);
    createDistribution();
  }

  public void createDistribution(){
    try{
      FileReader reader = new FileReader(Compression.file);
      BufferedReader bf = new BufferedReader(reader);
      String line;

      while((line = bf.readLine()) != null){
        String[] cut = line.split(" ");
        Node node = new Node(Integer.parseInt(cut[0]), Integer.parseInt(cut[1]));
        sortedInsert(node);
        arrCount++;
      }
    }catch(Exception e){}

		huffman();
  }

  public void huffman(){
		Node node1 = new Node();

		while(arrCount != 1){
			node1 = createHuffman();
			sortedInsert(node1);
		}
		traverseTree(first, code);

    String chunk = "";
    File file = new File(HuffmanFrame.huffName +".pards");
    try{
      FileWriter fw = new FileWriter(file);

      for(int i = 0 ; i < intArray.length; i++){
        printBinary(first, intArray[i]);

        if(binaryCode.length() >= 7){
           while(binaryCode.length() >= 7){
             chunk = binaryCode.substring(0, 7);
             binaryCode = binaryCode.substring(7, binaryCode.length());
            fw.write(getChunk(chunk));
           }
        }

        if(i == intArray.length - 1){
          if(binaryCode.length() < 7){
            chunk = binaryCode;
            while(chunk.length() != 7){
              chunk += "0";
            }
            fw.write(getChunk(chunk));
            binaryCode = "";
          }
        }
      }
      fw.close();
    }catch(Exception e){}

    String p = "";
    try{
      InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
      int num;

      while((num = reader.read()) != -1){
        p = Integer.toBinaryString(num);
        String ex = "";
        for(int i = 0; i < 7-p.length(); i++){
          ex += "0";
        }
        String x = ex + p;
        imageCode += x;
      }
    }catch(Exception e){}

    Node currently = first;
    for(int i = 0; i < imageCode.length(); i++){
      char ch = imageCode.charAt(i);
      if(currently.left == null && currently.right == null){
        rgbCharacters[ctr++] = currently.getChar();
        currently = first;
      }
      if(currently.left != null && currently.right != null){
        if(ch == '0'){
          currently = currently.left;
        }else if(ch == '1'){
          currently = currently.right;
        }
      }
    }
	}

  public Node createHuffman(){
		Node temp;
		Node node1 = new Node();

		for(int i = 1; i <=2; i++){
			try{
				temp = first;
				first = first.next;
				if(i == 1){
					node1.left = temp;
					node1.left.setChar(temp.getChar());
					node1.setFrequency(temp.getFrequency());
				}
				if(i == 2){
					node1.right = temp;
					node1.right.setChar(temp.getChar());
					node1.setFrequency(node1.getFrequency() + temp.getFrequency());
					node1.setChar(node1.getFrequency());
				}
			}catch(NullPointerException e){}
		}

		try{
			arrCount--;
		}catch(NullPointerException e){}

		return node1;
	}

  public char getChunk(String str){
    int num = Integer.parseInt(str, 2);
    char ascii =(char) num;
    return ascii;
  }

  public void sortedInsert(Node new_node){
    Node current;

    if (first == null || first.getFrequency() >= new_node.getFrequency()){
    	new_node.next = first;
    	first = new_node;
    }else{
       current = first;
       while (current.next != null && current.next.getFrequency() < new_node.getFrequency())
    	   current = current.next;

       	new_node.next = current.next;
       	current.next = new_node;
    }
  }

  public void traverseTree(Node current, String code){
		if(current != null){
			if(current.left == null && current.right == null){
				current.setBitString(code);
			}
			if(current.right != null){
				traverseTree(current.right, code + "1");
			}
			if(current.left != null){
				traverseTree(current.left, code + "0");
			}
		}
	}

  public void printBinary(Node current, int c){
		if(current != null){
			if(current.left == null && current.right == null && current.getChar() == c){
				binaryCode += current.getBitString();
			}
			if(current.left != null){
				printBinary(current.left, c);
			}
			if(current.right != null){
				printBinary(current.right, c);
			}
		}
	}
}
