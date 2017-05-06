import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class DrawPanel extends JPanel implements Runnable{

  public static BufferedImage imgx;
  public static BufferedImage imgx2;
  public static BufferedImage orig;


  private BufferedImage imgy;

  private JPanel compressedPanel;

  private JLabel compressedLabel;

  private Thread animator;

  private Graphics2D g2;

  public DrawPanel(BufferedImage imgy, BufferedImage orig, JPanel compressedPanel, JLabel compressedLabel){
    this.compressedLabel = compressedLabel;
    this.compressedPanel = compressedPanel;
    this.imgy = imgy;
    this.orig = orig;

    this.setPreferredSize(new Dimension(HuffmanFrame.x2 - HuffmanFrame.x1, HuffmanFrame.y2 - HuffmanFrame.y1));

    imgx = new BufferedImage(HuffmanFrame.x2 - HuffmanFrame.x1, HuffmanFrame.y2 - HuffmanFrame.y1, BufferedImage.TYPE_INT_ARGB);
    imgx2 = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_ARGB);

    g2 = (Graphics2D) imgx.getGraphics();
    g2 = (Graphics2D) imgx2.getGraphics();

    compressedPanel.add(this);
  }
  @Override
  public void addNotify() {
    super.addNotify();
    animator = new Thread(this);
    animator.start();
  }
  @Override
  public void run() {
    try {
      int ctr = 0;
      for (int i = 0; i <orig.getHeight(); i++) {
        for (int j = 0; j <orig.getWidth(); j++) {
          imgx2.setRGB(j, i, HuffmanTree.rgbCharacters[ctr++]);
        }
      }
      ctr = 0;
      for (int i = 0; i <HuffmanFrame.y2 - HuffmanFrame.y1; i++) {
        for (int j = 0; j <HuffmanFrame.x2 - HuffmanFrame.x1; j++) {
          try{
            Thread.sleep(1);


            imgx.setRGB(j, i, imgy.getRGB(j, i));
            compressedLabel.setIcon(new ImageIcon(imgx));

            compressedPanel.repaint();
            compressedPanel.revalidate();

            compressedLabel.repaint();
            compressedLabel.revalidate();

            repaint();
            revalidate();
          }catch(Exception e){}
        }

      }
      HuffmanFrame.statusbar.setText("DONE");
      HuffmanFrame.resetFlag = false;
      HuffmanFrame.saveFlag = false;
      HuffmanFrame.buttonLabel[1].setEnabled(true);
      HuffmanFrame.buttonLabel[6].setEnabled(true);

      HuffmanFrame.buttonLabel[1].addMouseListener(HuffmanFrame.handler);
      HuffmanFrame.buttonLabel[6].addMouseListener(HuffmanFrame.handler);
      // System.out.println("TAPOS NA?");
    }catch(Exception e){
    }
  }
}
