package com.knu.exam;

import com.knu.exam.dao.SpaceDao;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner SCANNER = new Scanner(System.in);
            System.out.print("Find planets with live in galaxy: ");
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
