# IR2ASM

A Java converter from FTIR proprietary data to Allotrope's ASM data

A project from [IFP Energies Nouvelles](https://www.ifpenergiesnouvelles.com/), a public research, innovation and training organization in the fields of energy, transport and the environment

## Usage

        String filePath = pathToIRFile;
        InfraredToAllotropeJsonConverter converter = new InfraredToAllotropeJsonConverter();
        List<ObjectNode> result = converter.convertFile(filePath);

## Supported files
- SPC files

## License
The code is available under the [CeCILL 2.1](https://cecill.info/licences/Licence_CeCILL_V2.1-fr.txt) licence, which is compatible with GNU GPL, GNU Affero GPL and EUPL.
The [ASM JSON schemas] (https://www.allotrope.org/asm) are available under CC-BY-NC 4.0 terms.