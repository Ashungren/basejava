/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    private int getIndex(String uuid) {
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
        int index = getIndex(rOld.toString());
        if (index == -1) {
            System.out.println("Resume " + rOld.toString() + " not exist");
        } else {
            storage[index] = rNew;
        }
    }

    void save(Resume r) {
        if (getIndex(r.toString()) != -1) {
            System.out.println("Resume " + r.toString() + " already exist");
        } else if (size == storage.length) {
            System.out.println("Storage overflow");
        } else {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    int size() {
        return size;
    }
}
