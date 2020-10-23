package com.ravensim.simulator.logic_gate;

import com.ravensim.simulator.boolean_function.BooleanFunction;
import com.ravensim.simulator.boolean_function.NotDecorator;
import com.ravensim.simulator.boolean_function.XorDecorator;
import com.ravensim.simulator.io.IncompatibleBitWidthsException;
import com.ravensim.simulator.io.InvalidNumberOfPortsException;
import com.ravensim.simulator.port.InvalidBitWidthException;
import com.ravensim.simulator.port.Port;
import com.ravensim.simulator.simulation.SimulationEngine;

import java.util.List;

public class XnorGate extends XorGate {
  public XnorGate(SimulationEngine simulationEngine, List<Port> inputs, Port output)
      throws IncompatibleBitWidthsException, InvalidNumberOfPortsException {
    super(simulationEngine, inputs, output);
  }

  @Override
  protected BooleanFunction baseCase(BooleanFunction logicGate, Port previousLink, int inputIndex)
      throws InvalidBitWidthException, IncompatibleBitWidthsException {
    var newLink = new Port(context, io.getCollectiveBitWidth());
    return new NotDecorator(
        new XorDecorator(logicGate, previousLink, io.getInputs().get(inputIndex), newLink),
        newLink,
        io.getOutput());
  }

  @Override
  protected BooleanFunction defaultCase(BooleanFunction logicGate)
      throws IncompatibleBitWidthsException {
    Port newLink = null;
    try {
      newLink = new Port(context, io.getCollectiveBitWidth());
    } catch (InvalidBitWidthException e) {
      // Do nothing.
    }
    return new NotDecorator(
        new XorDecorator(logicGate, io.getInputs().get(0), io.getInputs().get(1), newLink),
        newLink,
        io.getOutput());
  }
}
