package com.knu.task4;

import mpi.MPI;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == 0) {
            int message = 2020;
            send(message, 1);
            receive(size - 1);
        } else {
            int message = receive(rank - 1);
            send(message, (rank == size - 1) ? 0 : rank + 1);
        }

        MPI.Finalize();
    }

    private static void send(int data, int destination) {
        int[] message = {data};
        System.out.println(message[0] + " sent to " + destination + " at " + MPI.COMM_WORLD.Rank());
        MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, destination, 1);
    }

    private static int receive(int source) {
        int[] message = {-1};
        MPI.COMM_WORLD.Recv(message, 0, 1, MPI.INT, source, 1);
        if (message[0] == -1) {
            System.out.println("Sorry, could not receive message from " + source);
            return -1;
        }
        System.out.println(message[0] + " received from " + source + " at " + MPI.COMM_WORLD.Rank());
        return message[0];
    }
}
