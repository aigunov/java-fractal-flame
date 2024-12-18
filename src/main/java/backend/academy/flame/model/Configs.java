package backend.academy.flame.model;

import lombok.Builder;

@Builder
public record Configs(int height,
                      int width,
                      int iterationCount,
                      int affineCount,
                      int symmetry,
                      int threadsCount,
                      TransformationFunction transform,
                      ImageFormat format) {
}
