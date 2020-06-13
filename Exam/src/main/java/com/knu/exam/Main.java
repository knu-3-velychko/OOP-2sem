package com.knu.exam;

import com.knu.exam.dao.SpaceDao;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Find planets with live in galaxy: ");
            String galaxy = SCANNER.nextLine();
            SpaceDao.getAlive(galaxy);

            SpaceDao.getMinRadMaxSat();

            SpaceDao.getThirdQuery();

            SpaceDao.getHottestGalaxy();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
