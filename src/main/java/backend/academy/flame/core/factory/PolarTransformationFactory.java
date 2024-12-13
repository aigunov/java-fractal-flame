package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.PolarTransform;
import backend.academy.flame.core.transforms.Transformation;

public class PolarTransformationFactory extends TransformationFactory {
    @Override
    Transformation createTransformation() {
        return new PolarTransform();
    }
}
