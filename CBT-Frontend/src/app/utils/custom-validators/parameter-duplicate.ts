import { ValidatorFn, AbstractControl } from '@angular/forms';
import { FormArray } from '@angular/forms';
import { ValidationErrors } from '@angular/forms';

export function parameterDuplicateCheck(
  formArray: AbstractControl
): ValidationErrors | null {
  const array = formArray as FormArray;
  let arrLength = array.controls.length;
  let uniqueParameters = new Set<String>();

  const pythonKeywords = [
    'False',
    'None',
    'True',
    'and',
    'as',
    'assert',
    'async',
    'await',
    'break',
    'class',
    'continue',
    'def',
    'del',
    'elif',
    'else',
    'except',
    'finally',
    'for',
    'from',
    'global',
    'if',
    'import',
    'in',
    'is',
    'lambda',
    'nonlocal',
    'not',
    'or',
    'pass',
    'raise',
    'return',
    'try',
    'while',
    'with',
    'yield',
  ];

  const javaKeywords = [
    'abstract',
    'assert',
    'boolean',
    'break',
    'byte',
    'case',
    'catch',
    'char',
    'class',
    'const',
    'continue',
    'default',
    'do',
    'double',
    'else',
    'enum',
    'extends',
    'final',
    'finally',
    'float',
    'for',
    'goto',
    'if',
    'implements',
    'import',
    'instanceof',
    'int',
    'interface',
    'long',
    'native',
    'new',
    'package',
    'private',
    'protected',
    'public',
    'return',
    'short',
    'static',
    'strictfp',
    'super',
    'switch',
    'synchronized',
    'this',
    'throw',
    'throws',
    'transient',
    'try',
    'void',
    'volatile',
    'while',
  ];
  const combinedKeywords = [...javaKeywords, ...pythonKeywords];

  for (let i = 0; i < array.controls.length; i++) {
    let val = array.controls.at(i)?.get('parameterName')?.value;
    if (combinedKeywords.includes(val)) {
      return { isKeyWord: true };
    }
    uniqueParameters.add(array.controls.at(i)?.get('parameterName')?.value);
  }
  if (uniqueParameters.size === arrLength) {
    return null;
  }

  return { hasDuplicate: true };
}
