# ConvolutionalNeuralNetwork
An attempt to create a Convolutional Neural Network using Java, from scratch.
<BR><BR><BR>
FeatureMap a = new FeatureMap(INPUT_RED);
//Here, you create a New Feature Map, and in the bracket, you choose the parent/previous raw input that you want to connect it to. 

a.applyConvolution(TOP_SOBEL); 
//Here, you choose the convolution you want to do. You may also choose maxPooling() or averagePooling()
