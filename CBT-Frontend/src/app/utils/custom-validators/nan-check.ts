import { ValidatorFn, AbstractControl } from '@angular/forms';

export const NaNCheckValidate: ValidatorFn = (
  control: AbstractControl
): { [key: string]: any } | null => {
  if (control.value) {
    let val: string = control.value.toString().trim();
    if (!isNaN(parseInt(val)) || val.length > 1) {
      return { TypeError: true };
    }
  }
  if (!control.value) {
    return { notFound: true };
  }
  console.log('no error');
  return null;
};
