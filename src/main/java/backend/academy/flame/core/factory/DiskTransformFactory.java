package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.DiskTransform;
import backend.academy.flame.core.transforms.Transformation;

public class DiskTransformFactory extends TransformationFactory {
    @Override
    Transformation createTransformation() {
        return new DiskTransform();
    }
}
