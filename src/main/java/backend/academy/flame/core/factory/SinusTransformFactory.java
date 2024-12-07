package backend.academy.flame.core.factory;

import backend.academy.flame.core.transforms.SinusTransform;
import backend.academy.flame.core.transforms.Transformation;

public class SinusTransformFactory extends TransformationFactory{
    @Override
    Transformation createTransformation() {
        return new SinusTransform();
    }
}
