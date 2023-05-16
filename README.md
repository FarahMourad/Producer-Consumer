# Producer-Consumer Simulation

This is a Java application designed by students from the CSED Faculty of Engineering at Alexandria University.
The program simulates a production line using the producer-consumer design pattern to address synchronization issues where different machines need to cooperate without data loss. The GUI is developed using JavaFX.
## How to Run

1. File > Open > [select the project folder].
2. If you don't have JavaFX, download it first from [here](https://gluonhq.com/products/javafx/) and extract the zipped file.
3. File > Project Structure > Libraries >
   - Add new project library > Java > [select the folder of JavaFX] > Native Library Location.
   - Inside the Library: Add > [select the folder lib from the folder of JavaFX].
   - OK.
4. Run > Edit Configurations >
   - For class Main, add the following lines in VM options:
   
        --module-path [path of JavaFX library on your laptop]\lib --add-modules javafx.controls,javafx.fxml
  
   - Click OK.
5. Run Main class.

Note: These steps are to run the project on IntelliJ IDEA.

## UML Class Diagram

![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/85fdc8d1-2f99-49c1-aa12-b168b9e1365d)
![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/e64f760f-f335-4e50-9782-366b36075d02)
![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/75fa3963-0932-4a27-bd3a-8da75a165859)


## Applied Design Patterns

The following design patterns are applied in this project:

- Observer design pattern is used to update the state of the machine, either consuming or empty, and to pass the color to the GUI.
- Façade design pattern is used to make it easy for client code to interact with the decoupled code.
- Snapshot design pattern is used to save the previous products, serving time in each machine, and the input rate.
- Producer-consumer design pattern is used to allow passing products between the producer "Queues" and consumers "Machines".

## Usage

### To add a queue
Click on the "Add Q" button. To add a machine, click on the "Add M" button.
### Connecting a Machine and a Queue
choose the first element from the first dropdown list "from" and the second from the second dropdown list "to". 
Then, the "Connect" button will be enabled, and you can click on it to connect them.
### Simulation
click on the "Simulate" button. 
### Replay 
to replay the previous simulation click on the "Replay" button. 
### Delete 
To clear the window, click on the "Clear" button.
## User Manual 
The first interface that is shown

![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/dbfacbc8-29b2-4045-a058-f2d41bd7877f)

After Adding the machines and queues you can add connections

![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/7230bb86-964f-485a-8f81-efd1a03d2528)

![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/179cc9c6-d087-4f00-84c0-fe81003502ca)

After Connecting you can choose to simulate

![image](https://github.com/FarahMourad/Producer-Consumer/assets/51189763/972fc904-d187-456e-92b2-9acae2695c1f)

Here's a demo that shows the [simuation](https://drive.google.com/file/d/1SvzQ1d6H-jtczlNnFR8dxravdcUhqPDC/view?usp=sharing)
You can then either start new simulation by clicking "Simulate" or replay the previous simulation by clicking "Replay"

## Technologies Used
This application is written in Java and uses JavaFX for the GUI. The program uses the producer-consumer design pattern to address synchronization issues and to allow passing products between the producer "Queues" and consumers "Machines".

## Contributors

This application was developed by students from the CSED Faculty of Engineering at Alexandria University. We would like to thank our instructor, Dr. Khaled Nagi, for his guidance and support throughout the development of this project.
