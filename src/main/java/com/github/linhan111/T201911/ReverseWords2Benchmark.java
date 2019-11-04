package com.github.linhan111.T201911;

import com.github.linhan111.leetcode.reverseWords2;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * jmh about {@link reverseWords2}
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5, time = 2)
@Threads(4)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ReverseWords2Benchmark {

    @Benchmark
    public void testStringAdd() {
        String[] words = "Let's take LeetCode contest".split(" ");
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            StringBuilder builder2 = new StringBuilder(words[i].length());
            // 翻转，可通过双指针或这种方式，具体取决于内存及效率等
            for (int j = words[i].toCharArray().length - 1; j >= 0; j--) {
                builder2.append(words[i].toCharArray()[j]);
            }
            if (i == 0) {
                builder1.append(builder2.toString());
            } else {
                builder1.append(" ").append(builder2.toString());
            }
        }
        System.out.println(builder1.toString());
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().warmupIterations(5)
                .measurementIterations(5)
                .forks(2)
                .build();

        new Runner(options).run();
    }
}
