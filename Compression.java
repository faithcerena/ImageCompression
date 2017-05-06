import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;
import java.util.HashSet;

public class Compression  {
  int size;
  int[] num;
  int[][] freq2D;
  static File file = new File("save.huff");;
  // int[] intArray;

  public Compression(File file){
    try{
      BufferedImage image = ImageIO.read(file);
      marchThroughImage(image);
    }catch(Exception e){
    }
  }

  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    // System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
  }

  private void marchThroughImage(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    int[] intArray = new int[w*h];
    int[] num = new int[w*h];
    Set<Integer> colors = new HashSet<Integer>();

    //RGB VALUES W/ COORDINATES
    // System.out.println("width, height: " + w + ", " + h);

    int k = 0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        // System.out.println("x, y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        Color col = new Color(pixel, true);
        // System.out.println(col);

        printPixelARGB(pixel);
        num[k] = pixel;
        intArray[k] = pixel;
        colors.add(pixel);
        k++;
      }
    }

    int nbOccurences = 1;
    int count = 0;

    freq2D = new int[colors.size()+1][2];

    //FREQUENCY OF PIXELS
    System.out.println("\n[C H E C K  F R E Q U E N C Y]");
    Arrays.sort(intArray);
    for (int i = 0, length = intArray.length; i < length; i++) {
      if (i < length - 1) {
        if (intArray[i] == intArray[i + 1]) {
          nbOccurences++;
        }
      } else {
        // System.out.println(intArray[i] + " occurs " + nbOccurences + "x"); //end of array
        freq2D[count][0] = nbOccurences;
        freq2D[count][1] = intArray[i];
        count++;
      }
      if (i < length - 1 && intArray[i] != intArray[i + 1]) {
        // System.out.println(intArray[i] + " occurs " + nbOccurences + "x"); //moving to new element in array
        freq2D[count][0] = nbOccurences;
        freq2D[count][1] = intArray[i];
        count++;
        nbOccurences = 1;
      }

    }

    //SORT DESCENDING
    for(int i = 0; i < colors.size(); i++){
      for(int j = 0; j < colors.size(); j++){
        if(freq2D[j][0] < freq2D[j+1][0]){
          int temp[] = freq2D[j];
          freq2D[j] = freq2D[j+1];
          freq2D[j+1] = temp;
        }
      }
    }

    System.out.println("\nTOTAL PIXELS: " + k);
    System.out.println("TOTAL UNIQUE COLORS: "+ colors.size());
    try{
      if(HuffmanFrame.huffFlag == true){
        FileWriter writer = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(writer);
        for(int i = 0; i < colors.size(); i++){
          String temp = freq2D[i][0] + " " + freq2D[i][1];
          bw.write(freq2D[i][0] + " " + freq2D[i][1]);
          bw.newLine();
        }
        bw.flush();
        bw.close();
      }else{
          FileWriter writer = new FileWriter(file, true);
          BufferedWriter bw = new BufferedWriter(writer);

          for(int i = 0; i < colors.size(); i++){
            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            String line;
            boolean flag = false;

            while((line = bf.readLine()) != null){
              String[] cut = line.split(" ");

              if(freq2D[i][1] == Integer.parseInt(cut[1])){
                flag = true;
                break;
              }
            }
            bf.close();

            if(flag == false){
              bw.write(freq2D[i][0] + " " + freq2D[i][1]);
              bw.newLine();
            }
          }
          bw.flush();
          bw.close();
      }
    }catch(Exception e){}
    size = colors.size();
    HuffmanTree huff =  new HuffmanTree(size, num, w, h);
  }
}
