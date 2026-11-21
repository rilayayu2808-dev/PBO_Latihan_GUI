import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 1. Membuat Interface
interface Peliharaan {
    String bermain();
}

// 2. Membuat Abstract Class
abstract class Hewan {
    protected final String nama;

    public Hewan(String nama) { this.nama = nama; }

    // Abstract Method
    public abstract String suara();

    // Concrete Method
    public String makan() { return nama + " sedang makan wortel."; }
}

// 3. Membuat Concrete Class Kelinci
class Kelinci extends Hewan implements Peliharaan {
    public Kelinci(String nama) { super(nama); }

    @Override
    public String suara() { return nama + " mengeluarkan suara: Ngik! Ngik!"; }

    @Override
    public String bermain() { return nama + " sedang melompat-lompat di taman."; }
}

// 4. Main Class + GUI
public class Main {
    private Kelinci kelinciku = new Kelinci("Milo");
    private final JFrame frame = new JFrame("Aktivitas Kelinci");
    private final JTextField txtNama = new JTextField("Milo", 10);
    private final JButton btnBuat = new JButton("Buat Kelinci");
    private final JButton btnSuara = new JButton("Suara");
    private final JButton btnMakan = new JButton("Makan");
    private final JButton btnBermain = new JButton("Bermain");
    private final JTextArea areaLog = new JTextArea("Kelinci aktif: Milo\n");

    public void launchFrame() {
        JPanel atas = new JPanel(new FlowLayout());
        atas.add(new JLabel("Nama Kelinci :"));
        atas.add(txtNama);
        atas.add(btnBuat);
        JPanel bawah = new JPanel(new GridLayout(1, 3, 10, 10));   // GridLayout
        bawah.add(btnSuara);
        bawah.add(btnMakan);
        bawah.add(btnBermain);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(atas, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(areaLog), BorderLayout.CENTER);
        frame.getContentPane().add(bawah, BorderLayout.SOUTH);

        ButtonHandler handler = new ButtonHandler();
        btnBuat.addActionListener(handler);
        btnSuara.addActionListener(handler);
        btnMakan.addActionListener(handler);
        btnBermain.addActionListener(handler);

        areaLog.setEditable(false);
        frame.addWindowListener(new CloseHandler());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(420, 300);
        frame.setVisible(true);
    }

    // Event Handling : inner class yang mengimplementasikan ActionListener
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object sumber = e.getSource();
            if (sumber == btnBuat) {
                String nama = txtNama.getText().trim();
                if (nama.equals("")) {
                    areaLog.append("Nama kelinci tidak boleh kosong!\n");
                } else {
                    kelinciku = new Kelinci(nama);
                    areaLog.append("Kelinci aktif: " + nama + "\n");
                }
            } else if (sumber == btnSuara) {
                areaLog.append(kelinciku.suara() + "\n");
            } else if (sumber == btnMakan) {
                areaLog.append(kelinciku.makan() + "\n");
            } else if (sumber == btnBermain) {
                areaLog.append(kelinciku.bermain() + "\n");
            }
        }
    }

    // Event Adapter : hanya meng-override windowClosing()
    private class CloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int pilihan = JOptionPane.showConfirmDialog(frame, "Yakin ingin keluar?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (pilihan == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new Main().launchFrame();
    }
}