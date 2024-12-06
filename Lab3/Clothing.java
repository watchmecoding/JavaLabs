import java.util.Objects;

public class Clothing {
    private String type;        // Тип одягу (наприклад, "Куртка", "Сорочка")
    private String size;        // Розмір (наприклад, "M", "L")
    private String color;       // Колір (наприклад, "Чорний", "Білий")
    private double price;       // Ціна
    private String brand;       // Бренд

    // Конструктор
    public Clothing(String type, String size, String color, double price, String brand) {
        this.type = type;
        this.size = size;
        this.color = color;
        this.price = price;
        this.brand = brand;
    }

    // Геттери
    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    // Метод toString для зручного виводу об'єкта
    @Override
    public String toString() {
        return "Clothing{" +
                "type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }

    // Переозначення equals() для порівняння об'єктів
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return Double.compare(clothing.price, price) == 0 &&
                type.equals(clothing.type) &&
                size.equals(clothing.size) &&
                color.equals(clothing.color) &&
                brand.equals(clothing.brand);
    }

    // Переозначення hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(type, size, color, price, brand);
    }
}

