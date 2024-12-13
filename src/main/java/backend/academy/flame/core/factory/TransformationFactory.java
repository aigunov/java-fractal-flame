package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.Transformation;

public abstract class TransformationFactory {
    public Transformation transformation() {
        return createTransformation();
    }

    abstract Transformation createTransformation();
}
