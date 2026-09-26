package models;

import database.dto.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Muscle model
 */
public class Muscle extends Dto {
    private String name;

    /**
     * Muscle Dto constructor
     *
     * @param id   muscle's id
     * @param name muscle's name
     */
    public Muscle(int id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Muscle Dto constructor
     *
     * @param name muscle's name
     */
    public Muscle(String name) {
        super(-1);
        this.name = name;
    }

    /**
     * Constructor used for the tests.
     */
    public Muscle() {
        super(-1);
    }

    public int getId() {
        return super.key;
    }

    public String getName() {
        return name;
    }

    /**
     * Convert a list of muscle into a list of muscle name
     * @param m list of string
     * @return a list of muscle name.
     */
    public static List<String> toString(List<Muscle> m){
        List<String> res = new ArrayList<>();
        m.forEach(muscle -> res.add(muscle.getName()));
        return res;
    }
}
