import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HuffmanFrame extends JFrame {

  static Handler handler;

  public static int x1;
  public static int y1;
  public static int x2;
  public static int y2;

  private static File latestFile;

  private static Image dimg;

  private static BufferedImage img;
  private static BufferedImage img2;

  private JPanel mainPanel;
  private JPanel originalPanel;
  private JPanel compressedPanel;
  private JPanel infoPanel1;
  private JPanel infoPanel2;

  private static JLabel label = new JLabel();
  private static JLabel l = new JLabel();

  static JLabel[] buttonLabel = new JLabel[8];

  private JLabel originalLabel = new JLabel(new ImageIcon("Images/original.png"));
  private JLabel compressedLabel = new JLabel(new ImageIcon("Images/compressed.png"));
  private JLabel icon1 = new JLabel(new ImageIcon("Images/info.png"));
  private JLabel icon2 = new JLabel(new ImageIcon("Images/info.png"));
  private JLabel fileName1 = new JLabel("FILE NAME: ", SwingConstants.CENTER);
  private JLabel fileName2 = new JLabel("FILE NAME: ", SwingConstants.CENTER);
  private JLabel fileSize1 = new JLabel("FILE SIZE: ", SwingConstants.CENTER);
  private JLabel fileSize2 = new JLabel("FILE SIZE: ", SwingConstants.CENTER);
  private JLabel fileDimension1 = new JLabel("FILE DIMENSION: ", SwingConstants.CENTER);
  private JLabel fileDimension2 = new JLabel("FILE DIMENSION: ", SwingConstants.CENTER);

  public static JLabel statusbar = new JLabel("NONE", SwingConstants.CENTER);

  private String[] labelString = {"open", "save", "compress", "", "", "", "reset", "exit"};

  static boolean openFlag = true;
  static boolean saveFlag = true;
  static boolean compressFlag = true;
  static boolean resetFlag = true;
  static boolean huffFlag = true;

  static String huffName;

  public HuffmanFrame(){
    handler = new Handler();
    mainPanel = new BackgroundPanel("Images/bgwithmenu.png");
    mainPanel.setLayout(null);
    add(mainPanel);

    infoPanel1 = new JPanel(null);
    infoPanel1.setBackground(new Color(35,37,39));
    infoPanel1.setVisible(false);
    infoPanel1.setBounds(68,101, 526, 607);
    mainPanel.add(infoPanel1);

    infoPanel2 = new JPanel(null);
    infoPanel2.setBackground(new Color(35,37,39));
    infoPanel2.setVisible(false);
    infoPanel2.setBounds(768,101, 526, 607);
    mainPanel.add(infoPanel2);

    fileName1.setFont(new Font("Open Sans", Font.BOLD, 30));
    fileSize1.setFont(new Font("Open Sans", Font.BOLD, 30));
    fileDimension1.setFont(new Font("Open Sans", Font.BOLD, 30));

    fileName2.setFont(new Font("Open Sans", Font.BOLD, 30));
    fileSize2.setFont(new Font("Open Sans", Font.BOLD, 30));
    fileDimension2.setFont(new Font("Open Sans", Font.BOLD, 30));

    fileName1.setForeground(new Color(166, 155, 156));
    fileSize1.setForeground(new Color(166, 155, 156));
    fileDimension1.setForeground(new Color(166, 155, 156));

    fileName2.setForeground(new Color(166, 155, 156));
    fileSize2.setForeground(new Color(166, 155, 156));
    fileDimension2.setForeground(new Color(166, 155, 156));

    fileName1.setBounds(0, 200, 526, 100);
    fileSize1.setBounds(0, 240, 526, 100);
    fileDimension1.setBounds(0, 280, 526, 100);

    fileName2.setBounds(0, 200, 526, 100);
    fileSize2.setBounds(0, 240, 526, 100);
    fileDimension2.setBounds(0, 280, 526, 100);

    infoPanel1.add(fileName1);
    infoPanel1.add(fileSize1);
    infoPanel1.add(fileDimension1);

    infoPanel2.add(fileName2);
    infoPanel2.add(fileSize2);
    infoPanel2.add(fileDimension2);

    compressedPanel = new JPanel(null);
    compressedPanel.setBackground(new Color(0,0,0,0));
    compressedPanel.setVisible(false);
    compressedPanel.setBounds(768, 101, 526, 607);
    mainPanel.add(compressedPanel);

    label = new JLabel("", SwingConstants.CENTER);
    label.setBounds(68,101, 526,607);
    mainPanel.add(label);

    statusbar.setBounds(580, 520, 200,200);
    statusbar.setForeground(new Color(77, 125, 141));
    statusbar.setFont(new Font("Open Sans", Font.BOLD, 15));
    mainPanel.add(statusbar);

    addLabel(icon1, 555, 63, 30, 30);
    addLabel(icon2, 1255, 63, 30, 30);
    addLabel(originalLabel, 68, 55, 526, 653);
    addLabel(compressedLabel, 768, 55, 526, 653);

    for(int i = 0, y = 0; i < labelString.length; i++, y+=47){
      buttonLabel[i] = new JLabel(new ImageIcon("Images/" + labelString[i] + ".png"));
      buttonLabel[i].addMouseListener(handler);
      addLabel(buttonLabel[i], 594, 217+y, 174, 43);
    }

    icon1.addMouseListener(handler);
    icon2.addMouseListener(handler);
  }

  public void addLabel(JLabel l, int x, int y, int w, int h){
    l.setBounds(x, y, w, h);
    mainPanel.add(l);
  }

  private class Handler extends MouseAdapter {
    public void mouseEntered(MouseEvent event){
      for(int i = 0; i < 8; i++){
        if(event.getSource() == buttonLabel[i]){
          buttonLabel[i].setIcon(new ImageIcon("Images/" + labelString[i] + "inv.png"));
        }
      }

      if(event.getSource() == icon1){
        infoPanel1.setVisible(true);
        icon1.setIcon(new ImageIcon("Images/infoInv.png"));

      }
      if(event.getSource() == icon2){
        infoPanel2.setVisible(true);
        icon2.setIcon(new ImageIcon("Images/infoInv.png"));

      }

      repaint();
      revalidate();

    }
    public void mouseExited(MouseEvent event){
      for(int i = 0; i < 8; i++){
        if(event.getSource() == buttonLabel[i]){
          buttonLabel[i].setIcon(new ImageIcon("Images/" + labelString[i] + ".png"));
        }
      }
      if(event.getSource() == icon1){
        infoPanel1.setVisible(false);
        icon1.setIcon(new ImageIcon("Images/info.png"));
      }
      if(event.getSource() == icon2){
        infoPanel2.setVisible(false);
        icon2.setIcon(new ImageIcon("Images/info.png"));

      }
      repaint();
      revalidate();

    }

    public void mouseClicked(MouseEvent event){
      if(event.getSource() == buttonLabel[0] && openFlag == true){
        statusbar.setText("OPENING A FILE");

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".png files", "png");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(HuffmanFrame.this);

        if (option == JFileChooser.APPROVE_OPTION){
          saveFlag = true;
          compressFlag = false;
          buttonLabel[1].setEnabled(false);
          buttonLabel[1].removeMouseListener(handler);
          buttonLabel[2].setEnabled(true);
          buttonLabel[2].addMouseListener(handler);
          buttonLabel[6].setEnabled(false);
          buttonLabel[6].removeMouseListener(handler);

          File[] sf = chooser.getSelectedFiles();
          String filelist = "nothing";
          if (sf.length > 0) filelist = sf[0].getName();
          for (int i = 1; i < sf.length; i++) {
            filelist += ", " + sf[i].getName();
          }
          statusbar.setText("FILE OPENED");

          img = null;
          File file = chooser.getSelectedFile();
          huffName = file.getName();
          huffName = huffName.substring(0, huffName.length() - 4);
          latestFile = file;
          try{
            img=ImageIO.read(file);
            img2=ImageIO.read(file);

            fitImage(img);
          }catch(IOException e1) {

          }

          fileName1.setText("<html><font color=a69b9c>FILE NAME: </font> <font color=4d7d8d>" + filelist + "</font></html>");
          fileSize1.setText("<html><font color=a69b9c>FILE SIZE: </font> <font color=4d7d8d>" + file.length()/1024 + " kB" + "</font></html>");
          fileDimension1.setText("<html><font color=a69b9c>FILE DIMENSION: </font> <font color=4d7d8d>" + img.getWidth() + " x " + img.getHeight() + " px" + "</font></html>");

        }else{
          compressFlag = true;
          statusbar.setText("CANCELED");
        }


      }
      if(event.getSource() == buttonLabel[1] && saveFlag == false){
        statusbar.setText("SAVING A FILE");

        BufferedImage image;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png");

        chooser.setFileFilter(filter);

        int option = chooser.showSaveDialog(HuffmanFrame.this);

        if(option == JFileChooser.APPROVE_OPTION) {
          File file = new File(chooser.getSelectedFile().getName());
          try{
            String fileName = file.getCanonicalPath();

            if (!fileName.endsWith(".png")) {
              file = new File(fileName + ".png");
            }
            ImageIO.write(DrawPanel.imgx2, "png", file);
          }catch (IOException e) {
            e.printStackTrace();
          }

          fileName2.setText("<html><font color=a69b9c>FILE NAME: </font> <font color=4d7d8d>" + file.getName() + "</font></html>");
          fileSize2.setText("<html><font color=a69b9c>FILE SIZE: </font> <font color=4d7d8d>" + ((file.length()/1024 == 0) ? file.length() + " b" : file.length()/1024 + " kB") + " </font></html>");

          // statusbar.setText("YOU SAVED " + ((chooser.getSelectedFile()!=null)?chooser.getSelectedFile().getName(): "NOTHING" ));
          statusbar.setText("FILE SAVED");

        }else{
          statusbar.setText("CANCELED");
        }

      }
      if(event.getSource() == buttonLabel[2] && compressFlag == false){
        int opt = JOptionPane.showConfirmDialog(null, "Use existing .huff file?", "choose one", JOptionPane.YES_NO_OPTION);
        if(opt == JOptionPane.YES_OPTION){
          huffFlag = false;
        }else{
          huffFlag = true;
        }

        statusbar.setText("COMPRESSING");
        openFlag = false;
        saveFlag = true;
        compressFlag = true;
        resetFlag = true;
        buttonLabel[0].setEnabled(false);
        buttonLabel[0].removeMouseListener(handler);
        buttonLabel[2].setEnabled(false);
        buttonLabel[2].removeMouseListener(handler);
        buttonLabel[6].setEnabled(false);
        buttonLabel[6].removeMouseListener(handler);

        l.setBounds(x1, y1, x2-x1, y2-y1);
        compressedPanel.add(l);
        compressedPanel.setVisible(true);

        fileDimension2.setText("<html><font color=a69b9c>FILE DIMENSION: </font> <font color=4d7d8d>" + img.getWidth() + " x " + img.getHeight() + " px" + "</font></html>");

        Compression compression = new Compression(latestFile);

        statusbar.setText("COMPRESSING");

        JPanel drawPanwl = new DrawPanel(toBufferedImage(dimg), img2, mainPanel, l);

      }

      if(event.getSource() == buttonLabel[6] && resetFlag == false){
        fileName2.setText("<html><font color=a69b9c>FILE NAME: </font> <font color=4d7d8d>" + "</font></html>");
        fileSize2.setText("<html><font color=a69b9c>FILE SIZE: </font> <font color=4d7d8d>" + "</font></html>");
        fileDimension2.setText("<html><font color=a69b9c>FILE DIMENSION: </font> <font color=4d7d8d>" + "</font></html>");
        statusbar.setText("IMAGE CLEARED");
        openFlag = true;
        saveFlag = true;
        buttonLabel[0].setEnabled(true);
        buttonLabel[1].setEnabled(false);
        buttonLabel[0].addMouseListener(handler);
        buttonLabel[1].removeMouseListener(handler);
        compressedPanel.remove(l);
        compressedPanel.repaint();
        compressedPanel.revalidate();
        repaint();
        revalidate();
      }

      if(event.getSource() == buttonLabel[7]){
        System.exit(0);
      }

    }
  }

  public static BufferedImage toBufferedImage(Image img)
  {
    if (img instanceof BufferedImage)
    {
      return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
  }
  public static void fitImage(Image img){
    int imgWidth = img.getWidth(null);
    int imgHeight = img.getHeight(null);

    double imgAspect = (double) imgHeight / imgWidth;

    int canvasWidth = 526;
    int canvasHeight = 601;

    double canvasAspect = (double) canvasHeight / canvasWidth;


    if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
      // the image is smaller than the canvas
      x1 = (canvasWidth - imgWidth)  / 2;
      y1 = (canvasHeight - imgHeight) / 2;
      x2 = imgWidth + x1;
      y2 = imgHeight + y1;
      System.out.println("MAS GUTI");

    } else {
      if (canvasAspect > imgAspect) {
        y1 = canvasHeight;
        // keep image aspect ratio
        canvasHeight = (int) (canvasWidth * imgAspect);
        y1 = (y1 - canvasHeight) / 2;
      } else {
        x1 = canvasWidth;
        // keep image aspect ratio
        canvasWidth = (int) (canvasHeight / imgAspect);
        x1 = (x1 - canvasWidth) / 2;
      }
      x2 = canvasWidth + x1;
      y2 = canvasHeight + y1;
      System.out.println("MAS DAKO");
    }
      dimg = img.getScaledInstance(x2-x1,y2-y1,Image.SCALE_SMOOTH);
      label.setPreferredSize(new Dimension(imgWidth, imgHeight));
      label.setIcon(new ImageIcon(dimg));


  }

  private class BackgroundPanel extends JPanel{
    String string;

    public BackgroundPanel(String string){
      this.string = string;
    }
    public void paintComponent(Graphics page){
      super.paintComponent(page);
      page.drawImage((new ImageIcon(string)).getImage(), 0, 0, null);
    }

  }
}
