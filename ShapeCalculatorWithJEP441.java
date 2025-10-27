import java.util.Scanner;

public class ShapeCalculatorWithJEP441 {

    record Circle(double diameter) {}
    record Rectangle(double width, double height) {}
    record Triangle(double a, double b, double c) {}
    record InvalidShape(String reason) {}

    public static String describe(Object shape) {
        return switch (shape) {
            case Circle(double d) -> {
                double r = d / 2;
                double area = Math.PI * r * r;
                double perimeter = Math.PI * d;
                yield String.format("Hình tròn: Đường kính = %.2f, Chu vi = %.2f, Diện tích = %.2f", d, perimeter, area);
            }

            case Rectangle(double w, double h) -> {
                double area = w * h;
                double perimeter = 2 * (w + h);
                yield String.format("Hình chữ nhật: Cạnh = %.2f x %.2f, Chu vi = %.2f, Diện tích = %.2f", w, h, perimeter, area);
            }

            case Triangle(double a, double b, double c) -> {
                if (a + b > c && a + c > b && b + c > a) {
                    double p = (a + b + c) / 2;
                    double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
                    yield String.format("Hình tam giác: Cạnh = %.2f, %.2f, %.2f, Chu vi = %.2f, Diện tích = %.2f",
                            a, b, c, (a + b + c), area);
                } else {
                    yield "Ba cạnh không hợp lệ để tạo thành tam giác.";
                }
            }

            case InvalidShape(String reason) -> "Lỗi: " + reason;

            default -> "Không xác định.";
        };
    }

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

        Object shape = switch (numbers.length) {
            case 1 -> new Circle(numbers[0]);
            case 2 -> new Rectangle(numbers[0], numbers[1]);
            case 3 -> new Triangle(numbers[0], numbers[1], numbers[2]);
            default -> new InvalidShape("Chỉ hỗ trợ tối đa 3 cạnh (1: tròn, 2: chữ nhật, 3: tam giác).");
        };

        System.out.println(describe(shape));
        sc.close();
    }
}
