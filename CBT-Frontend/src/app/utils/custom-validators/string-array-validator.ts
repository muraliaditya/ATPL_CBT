import { ValidatorFn, AbstractControl } from '@angular/forms';

export const StringArrayValidate: ValidatorFn = (
  control: AbstractControl
): { [key: string]: any } | null => {
  if (control.value) {
    let val: string = control.value.toString().trim();
    if (!val.startsWith('[') || !val.endsWith(']')) {
      return { TypeError: true };
    }
    if (val.startsWith('[') && val.endsWith(']')) {
      console.log('no errror');

      return null;
    }
    console.log('typeerror');
    return { TypeError: true };
  }
  if (!control.value) {
    return { notFound: true };
  }
  console.log('no error');
  return null;
};
