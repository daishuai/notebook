import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Daishuai
 * @version 1.0.0
 * @description 图片水印
 * @createTime 2023年05月19日 10:14:00
 */
public class ImageWaterMarkDemo {

    public static void main(String[] args) throws IOException {
        // 加载原始图片和水印图片
        BufferedImage originalImage = ImageIO.read(new File("zhanghanyun.jpg"));
        // 创建新的BufferedImage，用于绘制水印和原始图片
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int fontSize = 20;
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = combinedImage.createGraphics();
        // 绘制原始图片和水印图片
        graphics.drawImage(originalImage, 0, 0, null);
        // 添加换行符
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        String waterMarkText = "Hello, world!\nThis is a test.";
        String[] lines = waterMarkText.split("\n");
        int offsetLength = 0;
        int line = lines.length;
        for (String text : lines) {
            offsetLength = Math.max(calTextLength(text), offsetLength);
        }
        // 水印内容放置在右下角
        int x = width - (offsetLength + 1) * fontSize;
        int y = height - (line + 1) * lineHeight;
        for (String text : lines) {
            graphics.drawString(text, x, y);
            y += lineHeight;
        }
        // 保存新的图片
        try {
            ImageIO.write(combinedImage, "jpg", new File("combined_image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int calTextLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            int byteLength = String.valueOf(text.charAt(i)).getBytes(Charset.forName("utf-8")).length;
            length += Math.min(byteLength, 2);
        }
        return length / 2 + length % 2;
    }
}
