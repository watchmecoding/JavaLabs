import java.util.*;

/**
 * Клас вузла для двозв’язного списку.
 */
class Node<T> {
    T data;
    Node<T> prev;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

/**
 * Власна реалізація двозв’язного списку.
 */
class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public boolean remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    head = head.next;
                    if (head != null) head.prev = null;
                } else if (current == tail) {
                    tail = tail.prev;
                    if (tail != null) tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) return true;
            current = current.next;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

/**
 * Музичний альбом.
 */
class Album {
    private String name;
    private String artist;
    private List<String> tracks;

    public Album(String name, String artist, List<String> tracks) {
        this.name = name;
        this.artist = artist;
        this.tracks = new ArrayList<>(tracks);
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public List<String> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return "Album{name='" + name + "', artist='" + artist + "', tracks=" + tracks + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Album album = (Album) obj;
        return Objects.equals(name, album.name) && Objects.equals(artist, album.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist);
    }
}

/**
 * Типізована колекція, яка реалізує інтерфейс Set і використовує двозв’язний список.
 */
class AlbumSet implements Set<Album> {
    private final DoublyLinkedList<Album> albums;

    public AlbumSet() {
        albums = new DoublyLinkedList<>();
    }

    public AlbumSet(Album album) {
        albums = new DoublyLinkedList<>();
        add(album);
    }

    public AlbumSet(Collection<? extends Album> collection) {
        albums = new DoublyLinkedList<>();
        addAll(collection);
    }

    @Override
    public int size() {
        return albums.size();
    }

    @Override
    public boolean isEmpty() {
        return albums.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return albums.contains((Album) o);
    }

    @Override
    public Iterator<Album> iterator() {
        return albums.iterator();
    }

    @Override
    public Object[] toArray() {
        List<Album> list = new ArrayList<>();
        for (Album album : albums) {
            list.add(album);
        }
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        List<Album> list = new ArrayList<>();
        for (Album album : albums) {
            list.add(album);
        }
        return list.toArray(a);
    }

    @Override
    public boolean add(Album album) {
        if (!albums.contains(album)) {
            albums.add(album);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return albums.remove((Album) o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!albums.contains((Album) obj)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Album> c) {
        boolean modified = false;
        for (Album album : c) {
            if (add(album)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll not supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object obj : c) {
            if (remove(obj)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear not supported");
    }

    @Override
    public String toString() {
        return albums.toString();
    }
}

/**
 * Головний клас із інтерактивним меню.
 */
public class MusicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlbumSet albumSet = new AlbumSet();

        // Початкові альбоми
        albumSet.add(new Album("Album 1", "Artist A", Arrays.asList("Track 1", "Track 2", "Track 3")));
        albumSet.add(new Album("Album 2", "Artist B", Arrays.asList("Song A", "Song B")));
        albumSet.add(new Album("Album 3", "Artist C", Arrays.asList("Piece 1", "Piece 2", "Piece 3", "Piece 4")));

        while (true) {
            System.out.println("--- Меню ---");
            System.out.println("1. Переглянути всі альбоми");
            System.out.println("2. Додати новий альбом");
            System.out.println("3. Видалити альбом");
            System.out.println("4. Перевірити наявність альбому");
            System.out.println("5. Вийти");
            System.out.print("Оберіть дію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Споживаємо новий рядок

            switch (choice) {
                case 1:
                    System.out.println("Усі альбоми:");
                    for (Album album : albumSet) {
                        System.out.println(album + "\n");
                    }
                    System.out.println("Натисніть Enter, щоб вийти до меню.");
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.print("Введіть назву альбому: ");
                    String name = scanner.nextLine();

                    System.out.print("Введіть ім'я виконавця: ");
                    String artist = scanner.nextLine();

                    System.out.print("Введіть треки (через кому): ");
                    String tracksInput = scanner.nextLine();
                    List<String> tracks = Arrays.asList(tracksInput.split(",\\s*"));

                    Album newAlbum = new Album(name, artist, tracks);
                    albumSet.add(newAlbum);
                    System.out.println("Альбом додано!\n"); 
                    System.out.println("Натисніть Enter, щоб вийти до меню.");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.print("Введіть назву альбому для видалення: ");
                    String albumToRemove = scanner.nextLine();

                    Album foundAlbum = null;
                    for (Album album : albumSet) {
                        if (album.getName().equalsIgnoreCase(albumToRemove)) {
                            foundAlbum = album;
                            break;
                        }
                    }

                    if (foundAlbum != null) {
                        albumSet.remove(foundAlbum);
                        System.out.println("Альбом видалено!\n");
                        System.out.println("Натисніть Enter, щоб вийти до меню.");
                        scanner.nextLine();
                    } else {
                        System.out.println("Альбом не знайдено!\n");
                        System.out.println("Натисніть Enter, щоб вийти до меню.");
                        scanner.nextLine();
                    }
                    break;

                case 4:
                    System.out.print("Введіть назву альбому для перевірки: ");
                    String albumToCheck = scanner.nextLine();

                    boolean exists = false;
                    for (Album album : albumSet) {
                        if (album.getName().equalsIgnoreCase(albumToCheck)) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        System.out.println("Альбом існує!\n");
                        System.out.println("Натисніть Enter, щоб вийти до меню.");
                        scanner.nextLine();
                    } else {
                        System.out.println("Альбом не знайдено!\n");
                        System.out.println("Натисніть Enter, щоб вийти до меню.");
                        scanner.nextLine();
                    }
                    break;

                case 5:
                    System.out.println("\nДо побачення!");
                    return;

                default:
                    System.out.println("\nНевірний вибір. Спробуйте ще раз.\n");
            }
        }
    }
}
