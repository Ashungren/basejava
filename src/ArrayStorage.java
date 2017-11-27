/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    private int isPresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void update(Resume rOld, Resume rNew) {
        int check = isPresent(rOld.toString());
        if (check == -1) {
            System.out.println("Ошибка, резюме отсутствует в базе");
        } else {
            storage[check] = rNew;
        }
    }

    void save(Resume r) {
        int check = isPresent(r.toString());
        if (check == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Ошибка, резюме находится в базе");
        }
    }

    Resume get(String uuid) {
        Resume result = null;
        int check = isPresent(uuid);
        if (check == -1) {
            System.out.println("Ошибка, резюме отсутствует в базе");
        } else {
            result = storage[check];
        }
        return result;
    }

    void delete(String uuid) {
        int check = isPresent(uuid);
        if (check == -1) {
            System.out.println("Ошибка, попытка удаления несуществующего резюме");
        } else {
            storage[check] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    int size() {
        return size;
    }
}
