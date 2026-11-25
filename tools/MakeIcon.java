import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/*
 * Chuyển 1 ảnh PNG thành file .ico (icon Windows) để dùng cho FlappyBird.exe
 *
 * Cách chạy (ở thư mục gốc dự án):
 *   java tools/MakeIcon.java resources/images/birds/pixel_skin1_yellow.png icon/flappybird.ico
 *
 * - Ảnh được đặt vào giữa 1 khung vuông trong suốt (icon phải vuông).
 * - Phóng to kiểu "nearest neighbor" để pixel-art giữ nét, không bị mờ.
 * - Tạo sẵn các cỡ 16, 32, 48, 256 để Windows tự chọn cỡ phù hợp.
 */
public class MakeIcon {

    private static final int[] SIZES = {16, 32, 48, 256};

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Cach dung: java tools/MakeIcon.java <anh.png> <icon.ico>");
            return;
        }

        BufferedImage source = ImageIO.read(new File(args[0]));
        if (source == null) {
            System.out.println("Khong doc duoc anh: " + args[0]);
            return;
        }

        // Mỗi cỡ icon được lưu dưới dạng PNG bên trong file .ico
        List<byte[]> pngs = new ArrayList<>();
        for (int size : SIZES) {
            pngs.add(toPng(fitIntoSquare(source, size)));
        }

        File output = new File(args[1]);
        if (output.getParentFile() != null) {
            output.getParentFile().mkdirs();
        }
        writeIco(output, pngs);
        System.out.println("Da tao icon: " + output.getPath());
    }

    // Đặt ảnh vào giữa khung vuông size x size, giữ đúng tỉ lệ, chừa lề 1 chút
    private static BufferedImage fitIntoSquare(BufferedImage src, int size) {
        BufferedImage square = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        double padding = size * 0.04;
        double scale = Math.min((size - 2 * padding) / src.getWidth(), (size - 2 * padding) / src.getHeight());
        int w = (int) Math.round(src.getWidth() * scale);
        int h = (int) Math.round(src.getHeight() * scale);
        int x = (size - w) / 2;
        int y = (size - h) / 2;

        Graphics2D g = square.createGraphics();
        // Ảnh lớn thu nhỏ xuống icon nhỏ: dùng nội suy mượt để không mất chi tiết;
        // ảnh phóng to: giữ pixel vuông vức.
        Object hint = scale < 1
                ? RenderingHints.VALUE_INTERPOLATION_BILINEAR
                : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g.drawImage(src, x, y, w, h, null);
        g.dispose();
        return square;
    }

    private static byte[] toPng(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    }

    /*
     * Cấu trúc file .ico:
     *   ICONDIR (6 byte)            : reserved=0, type=1 (icon), số lượng ảnh
     *   ICONDIRENTRY x N (16 byte)  : kích thước, offset của từng ảnh
     *   dữ liệu ảnh (PNG)
     * Các số trong .ico là little-endian nên phải tự đảo byte.
     */
    private static void writeIco(File file, List<byte[]> pngs) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeShort(Short.reverseBytes((short) 0));
            out.writeShort(Short.reverseBytes((short) 1));
            out.writeShort(Short.reverseBytes((short) pngs.size()));

            int offset = 6 + 16 * pngs.size();
            for (int i = 0; i < pngs.size(); i++) {
                int size = SIZES[i];
                out.writeByte(size >= 256 ? 0 : size); // 0 nghĩa là 256
                out.writeByte(size >= 256 ? 0 : size);
                out.writeByte(0);                      // số màu (0 = không dùng bảng màu)
                out.writeByte(0);                      // reserved
                out.writeShort(Short.reverseBytes((short) 1));   // color planes
                out.writeShort(Short.reverseBytes((short) 32));  // bit mỗi pixel
                out.writeInt(Integer.reverseBytes(pngs.get(i).length));
                out.writeInt(Integer.reverseBytes(offset));
                offset += pngs.get(i).length;
            }

            for (byte[] png : pngs) {
                out.write(png);
            }
        }
    }
}
