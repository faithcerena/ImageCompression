import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class HuffmanTest {

  public static void main(String[] args){

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    HuffmanFrame huffmanFrame = new HuffmanFrame();
    huffmanFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    huffmanFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    huffmanFrame.setUndecorated(true);
    huffmanFrame.setVisible(true);
    huffmanFrame.setLocationRelativeTo(null);

  }
}
