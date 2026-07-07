package com.solvians.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {
    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int quotes) {
        this.threads = threads;
        this.quotes = quotes;
    }

    public Stream<CertificateUpdate> generateQuotes() {
        int total = threads * quotes;
        List<Callable<CertificateUpdate>> tasks = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            tasks.add(CertificateUpdate::new);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            List<Future<CertificateUpdate>> futures = executor.invokeAll(tasks);
            List<CertificateUpdate> updates = new ArrayList<>(total);
            for (Future<CertificateUpdate> future : futures) {
                updates.add(future.get());
            }
            return updates.stream();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while generating certificate updates", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to generate certificate update", e.getCause());
        } finally {
            executor.shutdown();
        }
    }
}
