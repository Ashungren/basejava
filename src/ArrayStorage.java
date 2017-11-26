/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;
    int check = -1;

    private boolean isPresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                check = i;
                return true;
            }
        }
        return false;
    }

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void update(Resume r) {
        if (isPresent(r.toString())) {
            System.out.println("Резюме обновлено");
            check = -1;
        } else {
            System.out.println("Ошибка, резюме отсутствует в базе");
        }
    }

    void save(Resume r) {
        if (isPresent(r.toString())) {
            check = -1;
            System.out.println("Ошибка, резюме находится в базе");
        } else {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        Resume result = null;
        if (isPresent(uuid)) {
            result = storage[check];
            check = -1;
        }
        return result;
    }

    void delete(String uuid) {
        if (isPresent(uuid)) {
            storage[check] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            check = -1;
        } else {
            System.out.println("Ошибка, попытка удаления несуществующего резюме");
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
