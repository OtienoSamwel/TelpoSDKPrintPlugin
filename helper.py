import glob
# print(glob.glob("/home/kingsman/Desktop/TelpoDemoSDK/app/src/main/jniLibs/arm64-v8a/*.so"))
import os
txtfiles = []
for file in glob.glob("/home/kingsman/Desktop/TelpoDemoSDK/app/src/main/jniLibs/arm64-v8a/*.so"):
    head, tail = os.path.split(file)
    # txtfiles.append(tail)
    print('<resource-file src="src/android/jnilibs/arm64-v8a/' + tail + '" target="jniLibs/arm64-v8a/' +tail + '"/>')

# print(txtfiles)