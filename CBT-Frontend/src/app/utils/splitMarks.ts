export function distributeMarks(
  totalMarks: number,
  questionsLength: number
): number[] {
  const weightages: number[] = [];
  if (questionsLength === 1) {
    weightages.push(totalMarks);
    return weightages;
  }

  const baseMark = Math.floor(totalMarks / questionsLength);
  let remMarks = totalMarks % questionsLength;

  for (let i = 0; i < questionsLength; i++) {
    weightages.push(baseMark);
  }

  let idx = 0;
  while (remMarks > 0) {
    weightages[idx]++;
    remMarks--;
    idx = (idx + 1) % questionsLength;
  }

  return weightages;
}
