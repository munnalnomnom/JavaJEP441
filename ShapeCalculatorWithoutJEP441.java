import java.util.Scanner;

public class ShapeCalculatorWithoutJEP441 {

    record Circle(double diameter) {}
    record Rectangle(double width, double height) {}
    record Triangle(double a, double b, double c) {}
    record InvalidShape(String reason) {}

    public static String describe(Object shape) {

        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            double d = c.diameter();
            double r = d / 2;
            double area = Math.PI * r * r;
            double perimeter = Math.PI * d;
            return String.format("Hình tròn: Đường kính = %.2f, Chu vi = %.2f, Diện tích = %.2f",
                    d, perimeter, area);
        }

        if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            double w = r.width();
            double h = r.height();
            double area = w * h;
            double perimeter = 2 * (w + h);
            return String.format("Hình chữ nhật: Cạnh = %.2f x %.2f, Chu vi = %.2f, Diện tích = %.2f",
                    w, h, perimeter, area);
        }

        if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            double a = t.a();
            double b = t.b();
            double c = t.c();
            if (a + b > c && a + c > b && b + c > a) {
                double p = (a + b + c) / 2;
                double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
                return String.format("Hình tam giác: Cạnh = %.2f, %.2f, %.2f, Chu vi = %.2f, Diện tích = %.2f",
                        a, b, c, (a + b + c), area);
            } else {
                return "Ba cạnh không hợp lệ để tạo thành tam giác.";
            }
        }

        if (shape instanceof InvalidShape) {
            InvalidShape invalid = (InvalidShape) shape;
            return "Lỗi: " + invalid.reason();
        }

        return "Không xác định.";
    }
    // --- Hàm main ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập các cạnh (tối đa 3 cạnh) với: \n1 cạnh tương ứng đường kính hình tròn\n2 cạnh là hình chữ nhật (hoặc vuông) \n3 cạnh là hình tam giasc\nCác cạnh cách nhau bởi dấu cách: ");
        String line = sc.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println(describe(new InvalidShape("Không có dữ liệu vào.")));
            return;
        }
        String[] parts = line.split("\\s+");
        double[] numbers = new double[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Double.parseDouble(parts[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println(describe(new InvalidShape("Dữ liệu nhập vào không phải là số.")));
            return;
        }
        Object shape;
        switch (numbers.length) {
            case 1 -> shape = new Circle(numbers[0]);
            case 2 -> shape = new Rectangle(numbers[0], numbers[1]);
            case 3 -> shape = new Triangle(numbers[0], numbers[1], numbers[2]);
            default -> shape = new InvalidShape("Chỉ hỗ trợ tối đa 3 cạnh (1: tròn, 2: chữ nhật, 3: tam giác).");
        }
        System.out.println(describe(shape));
        sc.close();
    }
}
