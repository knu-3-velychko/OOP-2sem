package org.example.algorithm;

import javafx.geometry.Point3D;

import java.util.Objects;

public class Vertex {
    private final Point3D point;
    private final int id;
    private final String name;

    public Vertex(int x, int y, int z, int id, String name) {
        point = new Point3D(x, y, z);
        this.id = id;
        this.name = name;
    }

    public Vertex(Point3D point, int id, String name) {
        this.point = point;
        this.id = id;
        this.name = name;
    }

    public Point3D getPoint() {
        return point;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDistance(Vertex vertex) {
        return (int) point.distance(vertex.point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id &&
                point.equals(vertex.point) &&
                name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, id, name);
    }
}
