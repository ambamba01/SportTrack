package ulb.database.dto;

import java.util.Objects;

/**
 * Generic class to build a Data transfer object. The data carried is known by
 * its key.
 */
public class Dto {

    /**
     * Key of the data.
     */
    protected int key;

    /**
     * Creates a new instance of Dto with the key of the data.
     *
     * @param key key of the data.
     */
    protected Dto(int key) {
        this.key = key;
    }

    /**
     * Empty constructor to be able to create a Dto without calling the super function to allocate the key.
     * Used because our database has some autoincrement.
     */
    protected Dto() {
    }

    public int getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dto other = (Dto) obj;
        return Objects.equals(this.key, other.key);
    }

    /**
     * Check if the string is valide (not empty or null)
     *
     * @throws IllegalArgumentException
     */
    public void isValid(String item, String field) throws IllegalArgumentException {
        if (item == null || item.isBlank()) {
            throw new IllegalArgumentException("The " + field + " field is empty");
        }
    }

}
