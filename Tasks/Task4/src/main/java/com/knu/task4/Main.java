package com.knu.task4;

import mpi.MPI;

public class Main {
    public static void main(String[] args) {
        MPI.Init(args);
        System.out.println("Test");
        MPI.Finalize();
    }
}
