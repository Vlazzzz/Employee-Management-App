import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePanel extends JPanel {
    public HomePanel() {
        super();

        // Create and set the title label with an EmptyBorder
        JLabel titleLabel = new JLabel("Employment Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bahnschrift", Font.BOLD, 40));
        titleLabel.setBorder(new EmptyBorder(64, 0, 16, 0));
        add(titleLabel/*, BorderLayout.NORTH*/);
        try {
            // Replace "assets/employmentHistory.png" with the correct file path of your farm image
            File imageFile = new File("assets/management.png");
            BufferedImage managementImage = ImageIO.read(imageFile);
            ImageIcon managementImageIcon = new ImageIcon(managementImage);
            managementImageIcon.setImage(managementImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(managementImageIcon, SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(600, 500));
            add(imageLabel/*, BorderLayout.CENTER*/);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomePanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        HomePanel homePanel = new HomePanel();
        frame.add(homePanel);
        frame.setVisible(true);
    }
}
