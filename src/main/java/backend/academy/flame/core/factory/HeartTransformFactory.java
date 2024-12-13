package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.HeartTransform;
import backend.academy.flame.core.transforms.Transformation;

public class HeartTransformFactory extends TransformationFactory {
    @Override
    Transformation createTransformation() {
        return new HeartTransform();
    }
}
