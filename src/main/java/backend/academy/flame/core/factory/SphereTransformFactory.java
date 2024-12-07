package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.SphereTransform;
import backend.academy.flame.core.transforms.Transformation;

public class SphereTransformFactory extends TransformationFactory {
    @Override
    Transformation createTransformation() {
        return new SphereTransform();
    }
}
